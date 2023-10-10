import request from '@/utils/request'

// 查询车辆使用审核列表
export function listReview(query) {
  return request({
    url: '/system/carReview/list',
    method: 'get',
    params: query
  })
}

// 查询车辆使用审核详细
export function getReview(carReviewId) {
  return request({
    url: '/system/carReview/' + carReviewId,
    method: 'get'
  })
}

// 新增车辆使用审核
export function addReview(data) {
  return request({
    url: '/system/carReview',
    method: 'post',
    data: data
  })
}

// 修改车辆使用审核
export function updateReview(data) {
  return request({
    url: '/system/carReview',
    method: 'put',
    data: data
  })
}

// 删除车辆使用审核
export function delReview(carReviewId) {
  return request({
    url: '/system/carReview/' + carReviewId,
    method: 'delete'
  })
}
