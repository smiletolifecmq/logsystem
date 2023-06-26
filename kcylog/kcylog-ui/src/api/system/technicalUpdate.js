import request from '@/utils/request'

// 查询技术更新列表
export function listUpdate(query) {
  return request({
    url: '/system/technicalUpdate/list',
    method: 'get',
    params: query
  })
}

// 查询技术更新详细
export function getUpdate(technicalId) {
  return request({
    url: '/system/technicalUpdate/' + technicalId,
    method: 'get'
  })
}

// 新增技术更新
export function addUpdate(data) {
  return request({
    url: '/system/technicalUpdate',
    method: 'post',
    data: data
  })
}

// 修改技术更新
export function updateUpdate(data) {
  return request({
    url: '/system/technicalUpdate',
    method: 'put',
    data: data
  })
}

// 删除技术更新
export function delUpdate(technicalId) {
  return request({
    url: '/system/technicalUpdate/' + technicalId,
    method: 'delete'
  })
}
