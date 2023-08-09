export function fetchProjectData(queryProjectParams) {
    return new Promise((resolve, reject) => {
      const xhr = new XMLHttpRequest();
      let projectUrl =
        "http://192.168.110.105:9090/oa/basicinfo/basicinfoforhiring.do?" +
        "pageNum=" +
        queryProjectParams.pageNum +
        "&pageSize=" +
        queryProjectParams.pageSize;
  
      if (
        queryProjectParams.serialNum != null &&
        queryProjectParams.serialNum != ""
      ) {
        projectUrl = projectUrl + "&serialNum=" + queryProjectParams.serialNum;
      } else {
        projectUrl = projectUrl + "&serialNum=";
      }
  
      xhr.open("GET", projectUrl, true);
      xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            resolve(response); // 请求成功时返回响应数据
          } else {
            reject(new Error("请求失败")); // 请求失败时返回错误
          }
        }
      };
  
      xhr.send();
    });
  }