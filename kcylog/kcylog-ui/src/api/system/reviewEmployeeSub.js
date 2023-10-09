import request from '@/utils/request'

// 查询雇工实际工作内容记录列表
export function listEmployee(query) {
  return request({
    url: '/system/reviewSubEmployee/list',
    method: 'get',
    params: query
  })
}

// 查询雇工实际工作内容记录详细
export function getEmployee(reviewEmployeeId) {
  return request({
    url: '/system/reviewSubEmployee/' + reviewEmployeeId,
    method: 'get'
  })
}

// 新增雇工实际工作内容记录
export function addEmployee(data) {
  return request({
    url: '/system/reviewSubEmployee',
    method: 'post',
    data: data
  })
}

// 修改雇工实际工作内容记录
export function updateEmployee(data) {
  return request({
    url: '/system/reviewSubEmployee',
    method: 'put',
    data: data
  })
}

// 删除雇工实际工作内容记录
export function delEmployee(reviewEmployeeId) {
  return request({
    url: '/system/reviewSubEmployee/' + reviewEmployeeId,
    method: 'delete'
  })
}

export function confirmEmployee(data) {
  return request({
    url: '/system/reviewSubEmployee/confirm_employee_info',
    method: 'put',
    data: data
  })
}
