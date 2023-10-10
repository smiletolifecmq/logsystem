import request from '@/utils/request'

// 查询车辆使用流程配置过程列表
export function listInfo(query) {
  return request({
    url: '/system/carReviewConfigInfo/list',
    method: 'get',
    params: query
  })
}

// 查询车辆使用流程配置过程详细
export function getInfo(infoId) {
  return request({
    url: '/system/carReviewConfigInfo/' + infoId,
    method: 'get'
  })
}

// 新增车辆使用流程配置过程
export function addInfo(data) {
  return request({
    url: '/system/carReviewConfigInfo',
    method: 'post',
    data: data
  })
}

// 修改车辆使用流程配置过程
export function updateInfo(data) {
  return request({
    url: '/system/carReviewConfigInfo',
    method: 'put',
    data: data
  })
}

// 删除车辆使用流程配置过程
export function delInfo(infoId) {
  return request({
    url: '/system/carReviewConfigInfo/' + infoId,
    method: 'delete'
  })
}
