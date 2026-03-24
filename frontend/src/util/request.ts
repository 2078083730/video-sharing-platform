/* 用来封装axios异步请求 */
import axios from "axios";

/* 默认后台服务器地址 */
const BASE_URL = "http://localhost:9090"

/* 当我们发送异步请求的时候 请求地址自动拼接BASE_URL前缀  */
const request = axios.create({
    baseURL:BASE_URL
})

export default request

