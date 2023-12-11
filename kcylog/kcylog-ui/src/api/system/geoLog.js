import request from '@/utils/request'

// 查询地理部门日志列表
export function listLog(query) {
  return request({
    url: '/system/geoLog/list',
    method: 'get',
    params: query
  })
}

// 查询地理部门日志详细
export function getLog(logId) {
  return request({
    url: '/system/geoLog/' + logId,
    method: 'get'
  })
}

// 新增地理部门日志
export function addLog(data) {
  return request({
    url: '/system/geoLog',
    method: 'post',
    data: data
  })
}

// 修改地理部门日志
export function updateLog(data) {
  return request({
    url: '/system/geoLog',
    method: 'put',
    data: data
  })
}

// 删除地理部门日志
export function delLog(logId) {
  return request({
    url: '/system/geoLog/' + logId,
    method: 'delete'
  })
}
