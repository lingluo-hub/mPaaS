package com.ibyte.framework.discovery.client.hystrix;

import com.ibyte.common.util.thread.ThreadLocalHolder;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Hystrix参数传递
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class TranVarConcurrencyStrategy extends HystrixConcurrencyStrategy {
    /**
     * 包裹回调
     *
     * @param callable
     * @param <T>
     * @return
     */
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return super.wrapCallable(new DelegatingTranVarContextCallable(callable, ThreadLocalHolder.getTranVars()));
    }


    /**
     * 封装可传递参数的回调类
     *
     * @param <T>
     */
    private class DelegatingTranVarContextCallable<T> implements Callable<T> {
        /**
         * 原有的回调
         */
        private final Callable<T> delegate;

        /**
         * 需要传递的线程变量
         */
        private Map<String, Object> tranVar;


        public DelegatingTranVarContextCallable(Callable<T> delegate, Map<String, Object> tranVar) {
            this.delegate = delegate;
            this.tranVar = tranVar != null ? tranVar : new HashMap<>(1);
        }

        @Override
        public T call() throws Exception {
            try {
                ThreadLocalHolder.begin();
                ThreadLocalHolder.getTranVars().putAll(tranVar);
                return delegate.call();
            } finally {
                ThreadLocalHolder.end();
            }
        }
    }
}