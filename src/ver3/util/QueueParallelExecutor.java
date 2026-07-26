package ver3.util;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class QueueParallelExecutor {

    public static <T> void execute(Queue<T> items, Consumer<T> action, int threadCount){
        
        // フェーサー
        Phaser phaser = new Phaser(0);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    
        while(true){
            // キューから取得
            T item = items.poll();

            if(item == null){
                if(phaser.getRegisteredParties() == 0){
                    break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
    
            phaser.register();
            
            executor.submit(() -> {
                try{
                    action.accept(item);
                }finally{
                    phaser.arriveAndDeregister();
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
