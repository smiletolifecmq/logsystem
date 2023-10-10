import request from '@/utils/request'

// 查询车辆审核流程配置列表
export function listConfig(query) {
  return request({
    url: '/system/carReviewConfig/list',
    method: 'get',
    params: query
  })
}

// 查询车辆审核流程配置详细
export function getConfig(reviewConfigId) {
  return request({
    url: '/system/carReviewConfig/' + reviewConfigId,
    method: 'get'
  })
}

// 新增车辆审核流程配置
export function addConfig(data) {
  return request({
    url: '/system/carReviewConfig',
    method: 'post',
    data: data
  })
}

// 修改车辆审核流程配置
export function updateConfig(data) {
  return request({
    url: '/system/carReviewConfig',
    method: 'put',
    data: data
  })
}

// 删除车辆审核流程配置
export function delConfig(reviewConfigId) {
  return request({
    url: '/system/carReviewConfig/' + reviewConfigId,
    method: 'delete'
  })
}
