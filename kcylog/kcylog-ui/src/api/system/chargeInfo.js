import request from '@/utils/request'

// 查询分包明细表列表
export function listInfo(query) {
  return request({
    url: '/system/chargeInfo/list',
    method: 'get',
    params: query
  })
}

// 查询分包明细表详细
export function getInfo(id) {
  return request({
    url: '/system/chargeInfo/' + id,
    method: 'get'
  })
}

// 新增分包明细表
export function addInfo(data) {
  return request({
    url: '/system/chargeInfo',
    method: 'post',
    data: data
  })
}

// 修改分包明细表
export function updateInfo(data) {
  return request({
    url: '/system/chargeInfo',
    method: 'put',
    data: data
  })
}

// 删除分包明细表
export function delInfo(id) {
  return request({
    url: '/system/chargeInfo/' + id,
    method: 'delete'
  })
}
