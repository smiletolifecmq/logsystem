import request from '@/utils/request'

// 查询审核单列表
export function listReview(query) {
  return request({
    url: '/system/review/list',
    method: 'get',
    params: query
  })
}

// 查询审核单详细
export function getReview(reviewId) {
  return request({
    url: '/system/review/' + reviewId,
    method: 'get'
  })
}

// 新增审核单
export function addReview(data) {
  return request({
    url: '/system/review',
    method: 'post',
    data: data
  })
}

// 修改审核单
export function updateReview(data) {
  return request({
    url: '/system/review',
    method: 'put',
    data: data
  })
}

// 删除审核单
export function delReview(reviewId) {
  return request({
    url: '/system/review/' + reviewId,
    method: 'delete'
  })
}

// 发起审核单申请
export function setReviewStatus(data) {
  return request({
    url: '/system/review/set_status',
    method: 'put',
    data: data
  })
}

// 查询待审核单列表
export function upcomingListReview(query) {
  return request({
    url: '/system/review/upcomingListReview',
    method: 'get',
    params: query
  })
}


// 修改审核是否通过
export function setReviewProcessStatus(data) {
  return request({
    url: '/system/review/set_review_process',
    method: 'put',
    data: data
  })
}