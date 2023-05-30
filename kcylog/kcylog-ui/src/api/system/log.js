import request from '@/utils/request'

// 查询用户日志列表
export function listLog(query) {
  return request({
    url: '/system/log/list',
    method: 'get',
    params: query
  })
}

// 查询用户日志详细
export function getLog(userLogId) {
  return request({
    url: '/system/log/' + userLogId,
    method: 'get'
  })
}

// 新增用户日志
export function addLog(data) {
  return request({
    url: '/system/log/add',
    method: 'post',
    data: data
  })
}

// 修改用户日志
export function updateLog(data) {
  return request({
    url: '/system/log',
    method: 'put',
    data: data
  })
}

// 删除用户日志
export function delLog(userLogId) {
  return request({
    url: '/system/log/' + userLogId,
    method: 'delete'
  })
}

// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/log/deptTree',
    method: 'get'
  })
}
