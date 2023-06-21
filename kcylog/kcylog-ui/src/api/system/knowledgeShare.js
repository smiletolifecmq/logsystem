import request from '@/utils/request'

// 查询知识分享列表
export function listShare(query) {
  return request({
    url: '/system/knowledgeShare/list',
    method: 'get',
    params: query
  })
}

// 查询知识分享详细
export function getShare(knowledgeId) {
  return request({
    url: '/system/knowledgeShare/' + knowledgeId,
    method: 'get'
  })
}

// 新增知识分享
export function addShare(data) {
  return request({
    url: '/system/knowledgeShare',
    method: 'post',
    data: data
  })
}

// 修改知识分享
export function updateShare(data) {
  return request({
    url: '/system/knowledgeShare',
    method: 'put',
    data: data
  })
}

// 删除知识分享
export function delShare(knowledgeId) {
  return request({
    url: '/system/knowledgeShare/' + knowledgeId,
    method: 'delete'
  })
}
