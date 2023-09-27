import request from '@/utils/request'

// 查询项目车辆使用情况登记列表
export function listCar(query) {
  return request({
    url: '/system/car/list',
    method: 'get',
    params: query
  })
}

// 查询项目车辆使用情况登记详细
export function getCar(carId) {
  return request({
    url: '/system/car/' + carId,
    method: 'get'
  })
}

// 新增项目车辆使用情况登记
export function addCar(data) {
  return request({
    url: '/system/car',
    method: 'post',
    data: data
  })
}

// 修改项目车辆使用情况登记
export function updateCar(data) {
  return request({
    url: '/system/car',
    method: 'put',
    data: data
  })
}

// 删除项目车辆使用情况登记
export function delCar(carId) {
  return request({
    url: '/system/car/' + carId,
    method: 'delete'
  })
}
