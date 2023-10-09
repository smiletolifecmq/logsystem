import docxtemplater from 'docxtemplater'
import PizZip from 'pizzip'
import JSZipUtils from 'jszip-utils'
import { saveAs } from 'file-saver'

export const exportDocx = (tempDocxPath, data, fileName) => {
    // 读取并获得模板文件的二进制内容
    JSZipUtils.getBinaryContent(tempDocxPath, (error, content) => {
        // input.docx是模板。我们在导出的时候，会根据此模板来导出对应的数据
        // 抛出异常
        if (error) {
            throw error
        }
        let opts = {};
        opts.centered = false;
        opts.getImage = function (tagValue) {
            return new Promise(function (resolve, reject) {
                JSZipUtils.getBinaryContent(tagValue, function (error, content) {
                    if (error) {
                        return reject(error);
                    }
                    return resolve(content);
                });
            });
        };
      
        // 创建一个JSZip实例，内容为模板的内容
        const zip = new PizZip(content)
        // 创建并加载docxtemplater实例对象
        const doc = new docxtemplater().loadZip(zip).compile();
        doc.resolveData({ ...data }).then(() => {
            doc.render()
            const out = doc.getZip().generate({
                type: 'blob',
                mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
            }) // Output the document using Data-URI
            saveAs(out, fileName)
        }).catch(err => console.log('errorsss', err))
    })
}
