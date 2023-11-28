import request from '@/utils/request'

// 查询项目列表
export function listProject(query) {
  return request({
    url: '/system/project/list',
    method: 'get',
    params: query
  })
}

// 查询项目详细
export function getProject(projectId) {
  return request({
    url: '/system/project/' + projectId,
    method: 'get'
  })
}

// 新增项目
export function addProject(data) {
  return request({
    url: '/system/project',
    method: 'post',
    data: data
  })
}

// 修改项目
export function updateProject(data) {
  return request({
    url: '/system/project',
    method: 'put',
    data: data
  })
}

// 删除项目
export function delProject(projectId) {
  return request({
    url: '/system/project/' + projectId,
    method: 'delete'
  })
}

export function listProjectSelected(reviewType) {
  return request({
    url: '/system/project/getProjectRelation/' + reviewType,
    method: 'get',
  })
}

export function listProjectOperate(query) {
  return request({
    url: '/system/project/listProjectOperate',
    method: 'get',
    params: query
  })
}

export function updateProjectValue(data) {
  return request({
    url: '/system/project/projectValue',
    method: 'put',
    data: data
  })
}

export function jsProjectCz(projectId, data) {
  return request({
    url: '/system/project/jsProjectCz/' + projectId,
    method: 'post',
    data: data
  })
}

export function listUpcomingProject(query) {
  return request({
    url: '/system/project/listUpcoming',
    method: 'get',
    params: query
  })
}
