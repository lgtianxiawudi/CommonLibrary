(function() {
    if (window.WebViewJavascriptBridge) {
        return;
     }

    var messagingIframe;
    var sendMessageQueue = [];
    var messageHandlers = {};

    var CUSTOM_PROTOCOL_SCHEME = 'wvjbscheme';
    var QUEUE_HAS_MESSAGE = '__WVJB_QUEUE_MESSAGE__';
    var QUEUE_HAS_MESSAGE_SYN = '__SYN_WVJB_QUEUE_MESSAGE__';

    var responseCallbacks = {};
    var uniqueId = 1;

    function registerHandler(handlerName, handler) {
        messageHandlers[handlerName] = handler;
    }

    function callHandlerSyn(handlerName, data) {
      var messageQueueString = JSON.stringify({ handlerName:handlerName, data:data });

      var result = Base64.encode(messageQueueString);

      var returnData = prompt(CUSTOM_PROTOCOL_SCHEME + '://'+QUEUE_HAS_MESSAGE_SYN+"/" + result);

      return returnData;
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

        var messageQueueString = sendMessageQueue;

        sendMessageQueue = [];

        return messageQueueString;

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
                console.log("WebViewJavascriptBridge: WARNING: no message.responseId for message from ObjC:", message);
            }
        // },1000);
    }

    function _handleMessageFromNative(messageJSON) {
        _dispatchMessageFromObjC(messageJSON);
    }

    function _sendMessageFromNative(messageJSON){
      var message = JSON.parse(messageJSON);
      var messageHandler;
      var responseCallback;
      if (message.callbackId) {
        messageHandler = messageHandlers[message.handlerName];
        if (!messageHandler) {
          return;
        }
        var messageHandlerResult = messageHandler(message.data);
        message.responseId = message.callbackId;
        message.callbackId = "";
        message.data = messageHandlerResult;
        return message;
      }
    }

    window.WebViewJavascriptBridge = {
            registerHandler: registerHandler,
            callHandlerSyn: callHandlerSyn,
            callHandler: callHandler,
            _fetchQueue: _fetchQueue,
            _handleMessageFromNative: _handleMessageFromNative,
            _sendMessageFromNative:_sendMessageFromNative
    };
})();
