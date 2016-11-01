(function() {
    if (window.WebViewJavascriptBridge) {
        return;
    }

    var messagingIframe;
    var sendMessageQueue = [];
    var messageHandlers = {};

    var CUSTOM_PROTOCOL_SCHEME = 'wvjbscheme';
    var QUEUE_HAS_MESSAGE = '__WVJB_QUEUE_MESSAGE__';

    var responseCallbacks = {};
    var uniqueId = 1;

    function registerHandler(handlerName, handler) {
        messageHandlers[handlerName] = handler;
    }

    function callHandler(handlerName, data, responseCallback) {
        if (arguments.length == 2 && typeof data == 'function') {
            responseCallback = data;
            data = null;
        }
        _doSend({ handlerName:handlerName, data:data }, responseCallback);
    }

    function _doSend(message, responseCallback) {


        if (responseCallback) {
            var callbackId = 'cb_'+(uniqueId++)+'_'+new Date().getTime();
            responseCallbacks[callbackId] = responseCallback;
            message['callbackId'] = callbackId;
        }

        sendMessageQueue.push(message);


        prompt(CUSTOM_PROTOCOL_SCHEME + '://' + QUEUE_HAS_MESSAGE);
    }

    function _fetchQueue() {

        var messageQueueString = JSON.stringify(sendMessageQueue);

        sendMessageQueue = [];

        var result = Base64.encode(messageQueueString);

        prompt(CUSTOM_PROTOCOL_SCHEME + '://return/_fetchQueue/' + result);

    }

    function _dispatchMessageFromObjC(messageJSON) {
        // setTimeout(function _timeoutDispatchMessageFromObjC() {
            var message = JSON.parse(messageJSON);
            var messageHandler;
            var responseCallback;

            if (message.responseId) {
                responseCallback = responseCallbacks[message.responseId];
                if (!responseCallback) {
                    return;
                }
                var result;
                try{
                        result = JSON.parse(message.responseData);
                }catch(e){
                        result = message.responseData;
                }
                responseCallback(result);
                delete responseCallbacks[message.responseId];
            } else {
                if (message.callbackId) {
                    var callbackResponseId = message.callbackId;
                    responseCallback = function(responseData) {
                        _doSend({ responseId:callbackResponseId, responseData:responseData });
                    };
                }

                var handler = messageHandlers[message.handlerName];
                try {
                    handler(message.data, responseCallback);
                } catch(exception) {
                    console.log("WebViewJavascriptBridge: WARNING: javascript handler threw.", message, exception);
                }
                if (!handler) {
                    console.log("WebViewJavascriptBridge: WARNING: no handler for message from ObjC:", message);
                }
            }
        // },1000);
    }

    function _handleMessageFromNative(messageJSON) {
        _dispatchMessageFromObjC(messageJSON);
    }

    window.window.WebViewJavascriptBridge = {
            registerHandler: registerHandler,
            callHandler: callHandler,
            _fetchQueue: _fetchQueue,
            _handleMessageFromNative: _handleMessageFromNative
    };
}
)();