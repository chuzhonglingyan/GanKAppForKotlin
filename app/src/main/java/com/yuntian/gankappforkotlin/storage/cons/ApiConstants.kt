package com.yuntian.gankappforkotlin.storage.cons

/**
 * @author guangleilei
 * @version 1.0 2017-04-06
 */
object ApiConstants {

    // 房产id
    const val HOUSE_ID = "5YyX5Lqs"

    // 头条TYPE
    const val  HEADLINE_TYPE = "headline"
    // 房产TYPE
    const val HOUSE_TYPE = "house"
    // 其他TYPE
    const val OTHER_TYPE = "list"

    // 头条id
    const val HEADLINE_ID = "T1348647909107"


    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    const val AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"


    /**
     * 新闻id获取类型
     *
     * @param id 新闻id
     * @return 新闻类型
     */
    fun getType(id: String): String {
        when (id) {
            HEADLINE_ID -> return HEADLINE_TYPE
            HOUSE_ID -> return HOUSE_TYPE
            else -> {
            }
        }
        return OTHER_TYPE
    }


}
