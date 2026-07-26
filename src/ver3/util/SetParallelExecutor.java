package ver3.util;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SetParallelExecutor {

    public static <T> void execute(Collection<T> items, Consumer<T> action, int threadCount) {
        
        if (items == null || items.isEmpty()){
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (T item : items) {
            executor.submit(() -> action.accept(item));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}