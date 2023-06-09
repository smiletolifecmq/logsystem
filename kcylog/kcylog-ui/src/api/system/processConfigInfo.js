import request from '@/utils/request'

// 查询流程配置过程列表
export function listInfo(query) {
  return request({
    url: '/system/processConfigInfo/list',
    method: 'get',
    params: query
  })
}

// 查询流程配置过程详细
export function getInfo(infoId) {
  return request({
    url: '/system/processConfigInfo/' + infoId,
    method: 'get'
  })
}

// 新增流程配置过程
export function addInfo(data) {
  return request({
    url: '/system/processConfigInfo',
    method: 'post',
    data: data
  })
}

// 修改流程配置过程
export function updateInfo(data) {
  return request({
    url: '/system/processConfigInfo',
    method: 'put',
    data: data
  })
}

// 删除流程配置过程
export function delInfo(infoId) {
  return request({
    url: '/system/processConfigInfo/' + infoId,
    method: 'delete'
  })
}
