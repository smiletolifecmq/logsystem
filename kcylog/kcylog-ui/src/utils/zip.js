import docxtemplater from 'docxtemplater'
import PizZip from 'pizzip'
import JSZipUtils from 'jszip-utils'
import { saveAs } from 'file-saver'
import JSZip from 'jszip';


export const exportMultipleDocx = (tempDocxPath, dataArray, zipFileName) => {
    const zip = new JSZip();
    const promises = [];

    dataArray.forEach((data, index) => {
        promises.push(
            new Promise((resolve, reject) => {
                JSZipUtils.getBinaryContent(tempDocxPath, (error, content) => {
                    if (error) {
                        reject(error);
                    }
                    const doc = new docxtemplater().loadZip(new PizZip(content)).compile();
                    doc.resolveData({ ...data }).then(() => {
                        doc.render();
                        const out = doc.getZip().generate({
                            type: 'blob',
                            mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
                        });
                        zip.file(`${data.user_name}_产值.docx`, out);
                        resolve();
                    }).catch(err => reject(err));
                });
            })
        );
    });

    Promise.all(promises).then(() => {
        zip.generateAsync({ type: 'blob' }).then((blob) => {
            saveAs(blob, zipFileName);
        });
    });
};