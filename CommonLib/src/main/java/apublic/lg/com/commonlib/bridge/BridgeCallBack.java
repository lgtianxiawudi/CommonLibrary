package apublic.lg.com.commonlib.bridge;

import java.util.List;

import apublic.lg.com.commonlib.util.StrUtil;

/**
 * Created by ligang967 on 16/10/31.
 */

public class BridgeCallBack implements BridgeCallBackFunction {
    @Override
    public void onCallBack(String data) {
        // deserializeMessage
        List<BrgideMessage> list = null;
        try {
            list = BrgideMessage.toArrayList(data);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            BrgideMessage m = list.get(i);
            //Js发送消息，接受回调
            BridgeCallBackFunction responseFunction = null;
            // if had callbackId
            final String callbackId = m.getCallbackId();
            if (!StrUtil.isEmpty(callbackId)) {
                responseFunction = new BridgeCallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        BrgideMessage responseMsg = new BrgideMessage();
                        responseMsg.setResponseId(callbackId);
                        responseMsg.setResponseData(data);
                        BridgeInstance.getInstance().queueMessage(responseMsg);
                    }
                };
            } else {
                responseFunction = new BridgeCallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        // do nothing
                    }
                };
            }
            BridgeHandler handler = null;
            if (!StrUtil.isEmpty(m.getHandlerName())) {
                handler = BridgeInstance.getInstance().messageHandlers.get(m.getHandlerName());
            }
            if (handler != null) {
                handler.handler(m.getData(), responseFunction);
            }
        }
    }
}
