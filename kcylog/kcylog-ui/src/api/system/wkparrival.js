import request from '@/utils/request'

// 查询经营到账统计列表
export function listArrival(query) {
  return request({
    url: '/system/wkparrival/list',
    method: 'get',
    params: query
  })
}

// 查询经营到账统计详细
export function getArrival(dzId) {
  return request({
    url: '/system/wkparrival/' + dzId,
    method: 'get'
  })
}

// 新增经营到账统计
export function addArrival(data) {
  return request({
    url: '/system/wkparrival',
    method: 'post',
    data: data
  })
}

// 修改经营到账统计
export function updateArrival(data) {
  return request({
    url: '/system/wkparrival',
    method: 'put',
    data: data
  })
}

// 删除经营到账统计
export function delArrival(dzId) {
  return request({
    url: '/system/wkparrival/' + dzId,
    method: 'delete'
  })
}

