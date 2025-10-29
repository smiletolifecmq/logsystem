import request from '@/utils/request'

// 查询送件列表
export function listSending(query) {
  return request({
    url: '/system/sending/list',
    method: 'get',
    params: query
  })
}

// 查询送件详细
export function getSending(id) {
  return request({
    url: '/system/sending/' + id,
    method: 'get'
  })
}

// 新增送件
export function addSending(data) {
  return request({
    url: '/system/sending',
    method: 'post',
    data: data
  })
}

// 修改送件
export function updateSending(data) {
  return request({
    url: '/system/sending',
    method: 'put',
    data: data
  })
}

// 删除送件
export function delSending(id) {
  return request({
    url: '/system/sending/' + id,
    method: 'delete'
  })
}
