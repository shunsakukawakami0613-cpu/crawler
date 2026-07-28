package ver3.util;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ParallelQueueExecutor {

    public static <T> void execute(Queue<T> items, Consumer<T> action, int threadCount){
        
        // フェーサー
        AtomicInteger activeTasks = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    
        while(true){
            // キューから取得
            T item = items.poll();

            if(item == null){
                if(activeTasks.get() == 0){
                    break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                continue;
            }
    
            activeTasks.incrementAndGet();
            
            executor.submit(() -> {
                try{
                    action.accept(item);
                }finally{
                    activeTasks.decrementAndGet();
                }
            });
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
