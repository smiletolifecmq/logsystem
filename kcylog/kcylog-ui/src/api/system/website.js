import request from '@/utils/request'

// 查询网站整合列表
export function listWebsite(query) {
  return request({
    url: '/system/website/list',
    method: 'get',
    params: query
  })
}

// 查询网站整合详细
export function getWebsite(webId) {
  return request({
    url: '/system/website/' + webId,
    method: 'get'
  })
}

// 新增网站整合
export function addWebsite(data) {
  return request({
    url: '/system/website',
    method: 'post',
    data: data
  })
}

// 修改网站整合
export function updateWebsite(data) {
  return request({
    url: '/system/website',
    method: 'put',
    data: data
  })
}

// 删除网站整合
export function delWebsite(webId) {
  return request({
    url: '/system/website/' + webId,
    method: 'delete'
  })
}
