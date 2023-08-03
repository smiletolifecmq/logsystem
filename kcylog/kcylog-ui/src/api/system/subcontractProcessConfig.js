import request from '@/utils/request'

// 查询分包审核流程配置列表
export function listConfig(query) {
  return request({
    url: '/system/subcontractProcessConfig/list',
    method: 'get',
    params: query
  })
}

// 查询分包审核流程配置详细
export function getConfig(processConfigId) {
  return request({
    url: '/system/subcontractProcessConfig/' + processConfigId,
    method: 'get'
  })
}

// 新增分包审核流程配置
export function addConfig(data) {
  return request({
    url: '/system/subcontractProcessConfig',
    method: 'post',
    data: data
  })
}

// 修改分包审核流程配置
export function updateConfig(data) {
  return request({
    url: '/system/subcontractProcessConfig',
    method: 'put',
    data: data
  })
}

// 删除分包审核流程配置
export function delConfig(processConfigId) {
  return request({
    url: '/system/subcontractProcessConfig/' + processConfigId,
    method: 'delete'
  })
}
