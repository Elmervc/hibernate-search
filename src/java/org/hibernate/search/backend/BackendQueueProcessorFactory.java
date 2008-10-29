//$Id$
package org.hibernate.search.backend;

import java.util.Properties;
import java.util.List;

import org.hibernate.search.engine.SearchFactoryImplementor;

/**
 * Interface for different types of queue processor factories. Implementations need a no-arg constructor.
 * The factory typically prepares or pools the resources needed by the queue processor.
 *
 * @author Emmanuel Bernard
 */
public interface BackendQueueProcessorFactory {
	void initialize(Properties props, SearchFactoryImplementor searchFactory);

	/**
	 * Return a runnable implementation responsible for processing the queue to a given backend.
	 *
	 * @param queue The work queue to process.
	 * @return <code>Runnable</code> which processes <code>queue</code> when started.
	 */
	Runnable getProcessor(List<LuceneWork> queue);
}
