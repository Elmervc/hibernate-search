package org.hibernate.search;

import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.optimization.OptimizerStrategy;

/**
 * @author Emmanuel Bernard
 */
public interface InitAndRegisterContext extends InitContext {
	void addOptimizerStrategy(DirectoryProvider<?> provider, OptimizerStrategy optimizerStrategy);
}
