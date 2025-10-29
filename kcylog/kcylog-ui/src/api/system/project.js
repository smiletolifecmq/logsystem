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

export function listProjectHandover(query) {
  return request({
    url: '/system/project/listHandover',
    method: 'get',
    params: query
  })
}

export function listProjectWaitOneCheck(query) {
  return request({
    url: '/system/project/listProjectWaitOneCheck',
    method: 'get',
    params: query
  })
}

export function listProjectWaitTwoCheck(query) {
  return request({
    url: '/system/project/listProjectWaitTwoCheck',
    method: 'get',
    params: query
  })
}

export function listProjectStatisticsData(query) {
  return request({
    url: '/system/project/listProjectStatisticsData',
    method: 'get',
    params: query
  })
}

export function updateProjectCqBz(data) {
  return request({
    url: '/system/project/projectCqBz',
    method: 'put',
    data: data
  })
}

export function listProjectOperateCq(query) {
  return request({
    url: '/system/project/listProjectOperateCq',
    method: 'get',
    params: query
  })
}

export function listProductionDetails(query) {
  return request({
    url: '/system/project/listProductionDetails',
    method: 'get',
    params: query
  })
}

export function updateProjectDrawStatus(data) {
  return request({
    url: '/system/project/projectDrawStatus',
    method: 'put',
    data: data
  })
}

export function listProjectForFb(query) {
  return request({
    url: '/system/project/listForFb',
    method: 'get',
    params: query
  })
}

export function listProjectForFbTwoCheck(query) {
  return request({
    url: '/system/project/listForFbTwoCheck',
    method: 'get',
    params: query
  })
}

export function listProjectStatisticsDataForDept(query) {
  return request({
    url: '/system/project/listProjectStatisticsDataForDept',
    method: 'get',
    params: query
  })
}

export function getReviewProcessListForProjectId(query) {
  return request({
    url: '/system/process/subListForProject',
    method: 'get',
    params: query
  })
}

export function listProjectOperateValue(query) {
  return request({
    url: '/system/project/listProjectOperateValue',
    method: 'get',
    params: query
  })
}

export function updateCad(data) {
  return request({
    url: '/system/project/rangeLine',
    method: 'post',
    data: data
  })
}

export function updateProjectYgmoney(data) {
  return request({
    url: '/system/project/editYgmoney',
    method: 'put',
    data: data
  })
}

export function updateProjectCgsd(data) {
  return request({
    url: '/system/project/cgsd',
    method: 'put',
    data: data
  })
}

export function listProjectHj(query) {
  return request({
    url: '/system/project/listProjectHj',
    method: 'get',
    params: query
  })
}

export function listProjectHjMonth(query) {
  return request({
    url: '/system/project/listProjectHjMonth',
    method: 'get',
    params: query
  })
}

export function leLsSettleProject(data) {
  return request({
    url: '/system/project/leLsSettleProject',
    method: 'put',
    data: data
  })
}

export function listProjectYj(data) {
  return request({
    url: '/system/project/listProjectYj',
    method: 'put',
    data: data
  })
}

export function listProjectSj(data) {
  return request({
    url: '/system/project/listProjectSj',
    method: 'put',
    data: data
  })
}

export function listProjectYs(data) {
  return request({
    url: '/system/project/listProjectYs',
    method: 'put',
    data: data
  })
}

export function listProjectGd(data) {
  return request({
    url: '/system/project/listProjectGd',
    method: 'put',
    data: data
  })
}

export function listProjectStatisticsNbcz() {
  return request({
    url: '/system/project/listProjectStatisticsNbcz',
    method: 'get',
  })
}

export function nbcz(query) {
  return request({
    url: '/system/project/nbcz',
    method: 'get',
    params: query
  })
}

export function updateProjectNbcz(data) {
  return request({
    url: '/system/project/updateProjectNbcz',
    method: 'put',
    data: data
  })
}

export function updateProjectYjbz(data) {
  return request({
    url: '/system/project/updateProjectYjbz',
    method: 'put',
    data: data
  })
}