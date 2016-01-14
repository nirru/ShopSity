package com.oxilo.shopsity.MODAL;

import com.oxilo.shopsity.POJO.ModalLogin;

/**
 * Created by ericbasendra on 29/11/15.
 */
public class CustomModel {

    public interface OnCustomStateListener {
        void stateChanged(ModalLogin modalLogin);
    }

    private static CustomModel mInstance;
    private OnCustomStateListener mListener;
    private ModalLogin modalLogin;

    private CustomModel() {

    }
    public static CustomModel getInstance() {
        if(mInstance == null) {
            mInstance = new CustomModel();
        }
        return mInstance;
    }

    public void setListener(OnCustomStateListener listener) {
        mListener = listener;
    }

    public void changeState(ModalLogin modalLogin) {
        if(mListener != null) {
            this.modalLogin = modalLogin;
            notifyStateChange(modalLogin);
        }
    }

    public ModalLogin getState() {
        return modalLogin;
    }

    private void notifyStateChange(ModalLogin modalLogin) {
        mListener.stateChanged(modalLogin);
    }
}
