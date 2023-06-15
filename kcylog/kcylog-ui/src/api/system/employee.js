import request from '@/utils/request'

// 查询雇工列表
export function listReviewEmployee(query) {
  return request({
    url: '/system/employee/list',
    method: 'get',
    params: query
  })
}

// 查询雇工详细
export function getReviewEmployee(employeeId) {
  return request({
    url: '/system/employee/' + employeeId,
    method: 'get'
  })
}

// 新增雇工
export function addReviewEmployee(data) {
  return request({
    url: '/system/employee',
    method: 'post',
    data: data
  })
}

// 修改雇工
export function updateReviewEmployee(data) {
  return request({
    url: '/system/employee',
    method: 'put',
    data: data
  })
}

// 删除雇工
export function delReviewEmployee(employeeId) {
  return request({
    url: '/system/employee/' + employeeId,
    method: 'delete'
  })
}
