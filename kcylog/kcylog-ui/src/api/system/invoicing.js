import request from '@/utils/request'

// 查询经营开票统计列表
export function listInvoicing(query) {
  return request({
    url: '/system/invoicing/list',
    method: 'get',
    params: query
  })
}

// 查询经营开票统计详细
export function getInvoicing(kpId) {
  return request({
    url: '/system/invoicing/' + kpId,
    method: 'get'
  })
}

// 新增经营开票统计
export function addInvoicing(data) {
  return request({
    url: '/system/invoicing',
    method: 'post',
    data: data
  })
}

// 修改经营开票统计
export function updateInvoicing(data) {
  return request({
    url: '/system/invoicing',
    method: 'put',
    data: data
  })
}

// 删除经营开票统计
export function delInvoicing(kpId) {
  return request({
    url: '/system/invoicing/' + kpId,
    method: 'delete'
  })
}

export function getArrivalList(dzFph) {
  return request({
    url: '/system/arrival/getArrivalByDzFph/' + dzFph,
    method: 'get'
  })
}