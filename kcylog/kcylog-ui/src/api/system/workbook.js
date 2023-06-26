import request from '@/utils/request'

// 查询作业手册列表
export function listWorkbook(query) {
  return request({
    url: '/system/workbook/list',
    method: 'get',
    params: query
  })
}

// 查询作业手册详细
export function getWorkbook(workbookId) {
  return request({
    url: '/system/workbook/' + workbookId,
    method: 'get'
  })
}

// 新增作业手册
export function addWorkbook(data) {
  return request({
    url: '/system/workbook',
    method: 'post',
    data: data
  })
}

// 修改作业手册
export function updateWorkbook(data) {
  return request({
    url: '/system/workbook',
    method: 'put',
    data: data
  })
}

// 删除作业手册
export function delWorkbook(workbookId) {
  return request({
    url: '/system/workbook/' + workbookId,
    method: 'delete'
  })
}
