import request from '@/utils/request'

// 查询作业规范列表
export function listSpecification(query) {
  return request({
    url: '/system/specification/list',
    method: 'get',
    params: query
  })
}

// 查询作业规范详细
export function getSpecification(jobId) {
  return request({
    url: '/system/specification/' + jobId,
    method: 'get'
  })
}

// 新增作业规范
export function addSpecification(data) {
  return request({
    url: '/system/specification',
    method: 'post',
    data: data
  })
}

// 修改作业规范
export function updateSpecification(data) {
  return request({
    url: '/system/specification',
    method: 'put',
    data: data
  })
}

// 删除作业规范
export function delSpecification(jobId) {
  return request({
    url: '/system/specification/' + jobId,
    method: 'delete'
  })
}
