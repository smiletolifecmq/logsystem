import request from '@/utils/request'

// 查询地理项目列表
export function listProject(query) {
  return request({
    url: '/system/geoProject/list',
    method: 'get',
    params: query
  })
}

// 查询地理项目详细
export function getProject(projectId) {
  return request({
    url: '/system/geoProject/' + projectId,
    method: 'get'
  })
}

// 新增地理项目
export function addProject(data) {
  return request({
    url: '/system/geoProject',
    method: 'post',
    data: data
  })
}

// 修改地理项目
export function updateProject(data) {
  return request({
    url: '/system/geoProject',
    method: 'put',
    data: data
  })
}

// 删除地理项目
export function delProject(projectId) {
  return request({
    url: '/system/geoProject/' + projectId,
    method: 'delete'
  })
}
