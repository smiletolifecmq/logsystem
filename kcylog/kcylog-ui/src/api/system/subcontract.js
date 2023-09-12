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

export function getSubcontractProcessList(query) {
  return request({
    url: '/system/subcontractProcess/list',
    method: 'get',
    params: query
  })
}

// 发起审核单申请
export function setReviewStatus(data) {
  return request({
    url: '/system/subcontract/set_status',
    method: 'put',
    data: data
  })
}

export function upcomingListReview(query) {
  return request({
    url: '/system/subcontract/upcomingListReview',
    method: 'get',
    params: query
  })
}

// 修改审核是否通过
export function setSubcontractProcessStatus(data) {
  return request({
    url: '/system/subcontract/set_review_process',
    method: 'put',
    data: data
  })
}

// 批量通过
export function setBatchReviewPass(subcontractIds) {
  return request({
    url: '/system/subcontract/set_batch_review_pass/' + subcontractIds,
    method: 'put'
  })
}

export function doneListReview(query) {
  return request({
    url: '/system/subcontract/doneListReview',
    method: 'get',
    params: query
  })
}

// 查询已完成审核单列表
export function completedListReview(query) {
  return request({
    url: '/system/subcontract/completedListReview',
    method: 'get',
    params: query
  })
}

// 确认打印
export function confirmIsPrint(subcontractId) {
  return request({
    url: '/system/subcontract/set_is_print/' + subcontractId,
    method: 'put'
  })
}

export function getSubcontractBySerialNum(serialNum) {
  return request({
    url: '/system/subcontract/get_subcontract_by_serialNum/' + serialNum,
    method: 'get'
  })
}

export function listSubcontractReview(subcontractId) {
  return request({
    url: '/system/subcontract/getSubcontractReview/' + subcontractId,
    method: 'get'
  })
}
