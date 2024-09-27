import request from '@/utils/request'

// 查询经营归档列表
export function listArchive(query) {
  return request({
    url: '/system/archive/list',
    method: 'get',
    params: query
  })
}

// 查询经营归档详细
export function getArchive(technicalId) {
  return request({
    url: '/system/archive/' + technicalId,
    method: 'get'
  })
}

// 新增经营归档
export function addArchive(data) {
  return request({
    url: '/system/archive',
    method: 'post',
    data: data
  })
}

// 修改经营归档
export function updateArchive(data) {
  return request({
    url: '/system/archive',
    method: 'put',
    data: data
  })
}

// 删除经营归档
export function delArchive(technicalId) {
  return request({
    url: '/system/archive/' + technicalId,
    method: 'delete'
  })
}
