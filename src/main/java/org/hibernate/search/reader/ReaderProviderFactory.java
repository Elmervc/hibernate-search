//$Id$
package org.hibernate.search.reader;

import java.util.Map;
import java.util.Properties;

import org.hibernate.search.Environment;
import org.hibernate.search.cfg.SearchConfiguration;
import org.hibernate.search.engine.SearchFactoryImplementor;
import org.hibernate.search.util.PluginLoader;
import org.hibernate.util.StringHelper;

/**
 * @author Emmanuel Bernard
 */
public abstract class ReaderProviderFactory {

	private static Properties getProperties(SearchConfiguration cfg) {
		Properties props = cfg.getProperties();
		Properties workerProperties = new Properties();
		for (Map.Entry entry : props.entrySet()) {
			String key = (String) entry.getKey();
			if ( key.startsWith( Environment.READER_PREFIX ) ) {
				workerProperties.setProperty( key, (String) entry.getValue() );
			}
		}
		return workerProperties;
	}

	public static ReaderProvider createReaderProvider(SearchConfiguration cfg, SearchFactoryImplementor searchFactoryImplementor) {
		Properties props = getProperties( cfg );
		String impl = props.getProperty( Environment.READER_STRATEGY );
		ReaderProvider readerProvider;
		if ( StringHelper.isEmpty( impl ) ) {
			//put another one
			readerProvider = new SharingBufferReaderProvider();
		}
		else if ( "not-shared".equalsIgnoreCase( impl ) ) {
			readerProvider = new NotSharedReaderProvider();
		}
		else if ( "shared".equalsIgnoreCase( impl ) ) {
			readerProvider = new SharingBufferReaderProvider();
		}
		//will remove next "else":
		else if ( "shared-segments".equalsIgnoreCase( impl ) ) {
			readerProvider = new SharingBufferReaderProvider();
		}
		else {
			readerProvider = PluginLoader.instanceFromName( ReaderProvider.class, impl,
					ReaderProviderFactory.class, "readerProvider" );
		}
		readerProvider.initialize( props, searchFactoryImplementor );
		return readerProvider;
	}
}
