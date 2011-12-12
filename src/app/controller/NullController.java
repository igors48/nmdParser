package app.controller;

import app.iui.flow.custom.SingleProcessInfo;

/**
 * Пустой контроллер. Очень простой
 *
 * @author Igor Usenko
 *         Date: 20.03.2010
 */
public class NullController implements Controller {

    public void onStart() {
        // empty
    }

    public void onProgress(final SingleProcessInfo _info) {
        // empty
    }

    public void onComplete() {
        // empty
    }

    public void onFault() {
        // empty
    }

    public void onCancel() {
        // empty
    }

    public boolean isCancelled() {
        return false;
    }
    
}
