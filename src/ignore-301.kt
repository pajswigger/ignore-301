package burp


class BurpExtender: IBurpExtender {
    override fun registerExtenderCallbacks(callbacks: IBurpExtenderCallbacks) {
        callbacks.registerHttpListener(StatusCodeRewriter())
    }
}


class StatusCodeRewriter: IHttpListener {
    override fun processHttpMessage(toolFlag: Int, messageIsRequest: Boolean, messageInfo: IHttpRequestResponse) {
        if(toolFlag != IBurpExtenderCallbacks.TOOL_TARGET || messageIsRequest) {
            return
        }
        val response = messageInfo.response!!
        val statusCode = String(response.copyOfRange(9, 12))
        if(statusCode == "301") {
            response[9] = '4'.toByte()
            response[10] = '0'.toByte()
            response[11] = '4'.toByte()
            messageInfo.response = response
        }
    }
}
