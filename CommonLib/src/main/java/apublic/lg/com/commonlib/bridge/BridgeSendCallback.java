package apublic.lg.com.commonlib.bridge;

import android.text.TextUtils;

/**
 * Created by ligang967 on 16/11/3.
 */

public class BridgeSendCallback implements BridgeCallBackFunction {
    @Override
    public void onCallBack(String data) {
        BrgideMessage brgideMessage = BrgideMessage.toObject(data);
        if (TextUtils.isEmpty(brgideMessage.getResponseId())){
            return;
        }
        BridgeCallBackFunction bridgeCallBackFunction = BridgeInstance.getInstance().sendMessageCallBack.get(brgideMessage.getResponseId());
        if (bridgeCallBackFunction!=null){
            bridgeCallBackFunction.onCallBack(brgideMessage.getData());
        }
    }
}
