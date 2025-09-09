import request from '@/utils/request'

// 获取服务信息
export function processJzd(data) {
  return request({
    url: '/gis/jzd/process',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    responseType: 'blob',
    timeout: 300000,
    data: data
  })
}