package app.workingarea.process;

/**
 * Ёлемент статистики вызовов дл€ внешнего процесса
 *
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public class ProcessStatistic {

    private int succeded;
    private int failed;

    public void succeded() {
        this.succeded++;
    }

    public void failed() {
        this.failed++;
    }

    public int getFailed() {
        return this.failed;
    }

    public int getSucceded() {
        return this.succeded;
    }
    
}
