import request from '@/utils/request'

// 查询无经费服务项目统计列表
export function listProject(query) {
  return request({
    url: '/system/geoNofundingProject/list',
    method: 'get',
    params: query
  })
}

// 查询无经费服务项目统计详细
export function getProject(id) {
  return request({
    url: '/system/geoNofundingProject/' + id,
    method: 'get'
  })
}

// 新增无经费服务项目统计
export function addProject(data) {
  return request({
    url: '/system/geoNofundingProject',
    method: 'post',
    data: data
  })
}

// 修改无经费服务项目统计
export function updateProject(data) {
  return request({
    url: '/system/geoNofundingProject',
    method: 'put',
    data: data
  })
}

// 删除无经费服务项目统计
export function delProject(id) {
  return request({
    url: '/system/geoNofundingProject/' + id,
    method: 'delete'
  })
}
