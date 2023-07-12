import request from '@/utils/request'

// 查询项目进度管控列表
export function listProgress(query) {
  return request({
    url: '/system/projectProgress/list',
    method: 'get',
    params: query
  })
}

// 查询项目进度管控详细
export function getProgress(progressId) {
  return request({
    url: '/system/projectProgress/' + progressId,
    method: 'get'
  })
}

// 新增项目进度管控
export function addProgress(data) {
  return request({
    url: '/system/projectProgress',
    method: 'post',
    data: data
  })
}

// 修改项目进度管控
export function updateProgress(data) {
  return request({
    url: '/system/projectProgress',
    method: 'put',
    data: data
  })
}

// 删除项目进度管控
export function delProgress(progressId) {
  return request({
    url: '/system/projectProgress/' + progressId,
    method: 'delete'
  })
}
