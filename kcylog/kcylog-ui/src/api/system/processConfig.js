import request from '@/utils/request'

// 查询流程配置列表
export function listConfig(query) {
  return request({
    url: '/system/processConfig/list',
    method: 'get',
    params: query
  })
}

// 查询流程配置详细
export function getConfig(processConfigId) {
  return request({
    url: '/system/processConfig/' + processConfigId,
    method: 'get'
  })
}

// 新增流程配置
export function addConfig(data) {
  return request({
    url: '/system/processConfig',
    method: 'post',
    data: data
  })
}

// 修改流程配置
export function updateConfig(data) {
  return request({
    url: '/system/processConfig',
    method: 'put',
    data: data
  })
}

// 删除流程配置
export function delConfig(processConfigId) {
  return request({
    url: '/system/processConfig/' + processConfigId,
    method: 'delete'
  })
}
