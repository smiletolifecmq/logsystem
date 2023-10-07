import request from '@/utils/request'

// 查询结算单列表
export function listSettlement(query) {
  return request({
    url: '/system/settlementSub/list',
    method: 'get',
    params: query
  })
}

// 查询结算单详细
export function getSettlement(settlementId) {
  return request({
    url: '/system/settlementSub/' + settlementId,
    method: 'get'
  })
}

// 新增结算单
export function addSettlement(data) {
  return request({
    url: '/system/settlementSub',
    method: 'post',
    data: data
  })
}

// 修改结算单
export function updateSettlement(data) {
  return request({
    url: '/system/settlementSub',
    method: 'put',
    data: data
  })
}

// 删除结算单
export function delSettlement(settlementId) {
  return request({
    url: '/system/settlementSub/' + settlementId,
    method: 'delete'
  })
}

// 确认结算单
export function confirmSettlement(settlementId) {
  return request({
    url: '/system/settlementSub/confirm_settlement/' + settlementId,
    method: 'put'
  })
}
