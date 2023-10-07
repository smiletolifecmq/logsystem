import request from '@/utils/request'

// 查询审核单列表
export function listReview(query) {
  return request({
    url: '/system/reviewSub/list',
    method: 'get',
    params: query
  })
}

// 查询审核单详细
export function getReview(reviewId) {
  return request({
    url: '/system/reviewSub/' + reviewId,
    method: 'get'
  })
}

// 新增审核单
export function addReview(data) {
  return request({
    url: '/system/reviewSub',
    method: 'post',
    data: data
  })
}

// 修改审核单
export function updateReview(data) {
  return request({
    url: '/system/reviewSub',
    method: 'put',
    data: data
  })
}

// 删除审核单
export function delReview(reviewId) {
  return request({
    url: '/system/reviewSub/' + reviewId,
    method: 'delete'
  })
}

// 发起审核单申请
export function setReviewStatus(data) {
  return request({
    url: '/system/reviewSub/set_status',
    method: 'put',
    data: data
  })
}

// 查询待审核单列表
export function upcomingListReview(query) {
  return request({
    url: '/system/reviewSub/upcomingListReview',
    method: 'get',
    params: query
  })
}


// 修改审核是否通过
export function setReviewProcessStatus(data) {
  return request({
    url: '/system/reviewSub/set_review_process',
    method: 'put',
    data: data
  })
}

export function getReviewProcessList(query) {
  return request({
    url: '/system/process/subList',
    method: 'get',
    params: query
  })
}

// 查询已完成审核单列表
export function completedListReview(query) {
  return request({
    url: '/system/reviewSub/completedListReview',
    method: 'get',
    params: query
  })
}

export function doneListReview(query) {
  return request({
    url: '/system/reviewSub/doneListReview',
    method: 'get',
    params: query
  })
}


// 批量通过
export function setBatchReviewPass(reviewIds) {
  return request({
    url: '/system/reviewSub/set_batch_review_pass/' + reviewIds,
    method: 'put'
  })
}

// 开启编辑
export function setStartEdit(reviewId) {
  return request({
    url: '/system/reviewSub/set_start_edit/' + reviewId,
    method: 'put'
  })
}

// 生成结算单
export function setBatchEttlement(data) {
  return request({
    url: '/system/reviewSub/set_batch_review_settlement',
    method: 'put',
    data: data
  })
}

export function settlementListReview(query) {
  return request({
    url: '/system/reviewSub/settlementListReview',
    method: 'get',
    params: query
  })
}

export function getReviewBySerialNum(serialNum) {
  return request({
    url: '/system/reviewSub/get_review_by_serialNum/' + serialNum,
    method: 'get'
  })
}

export function listReviewSubcontract(reviewId) {
  return request({
    url: '/system/reviewSub/getReviewSubcontract/' + reviewId,
    method: 'get'
  })
}