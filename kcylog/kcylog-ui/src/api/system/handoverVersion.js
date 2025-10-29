import request from '@/utils/request'

// 查询多版本移交列表
export function listHandover(query) {
  return request({
    url: '/system/handoverVersion/list',
    method: 'get',
    params: query
  })
}

// 查询多版本移交详细
export function getHandover(id) {
  return request({
    url: '/system/handoverVersion/' + id,
    method: 'get'
  })
}

// 新增多版本移交
export function addHandover(data) {
  return request({
    url: '/system/handoverVersion',
    method: 'post',
    data: data
  })
}

// 修改多版本移交
export function updateHandover(data) {
  return request({
    url: '/system/handoverVersion',
    method: 'put',
    data: data
  })
}

// 删除多版本移交
export function delHandover(id) {
  return request({
    url: '/system/handoverVersion/' + id,
    method: 'delete'
  })
}
