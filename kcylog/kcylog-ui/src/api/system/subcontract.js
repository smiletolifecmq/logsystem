import request from '@/utils/request'

// 查询分包列表
export function listSubcontract(query) {
  return request({
    url: '/system/subcontract/list',
    method: 'get',
    params: query
  })
}

// 查询分包详细
export function getSubcontract(subcontractId) {
  return request({
    url: '/system/subcontract/' + subcontractId,
    method: 'get'
  })
}

// 新增分包
export function addSubcontract(data) {
  return request({
    url: '/system/subcontract',
    method: 'post',
    data: data
  })
}

// 修改分包
export function updateSubcontract(data) {
  return request({
    url: '/system/subcontract',
    method: 'put',
    data: data
  })
}

// 删除分包
export function delSubcontract(subcontractId) {
  return request({
    url: '/system/subcontract/' + subcontractId,
    method: 'delete'
  })
}
