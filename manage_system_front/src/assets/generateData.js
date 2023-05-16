const testdata = Array.from({ length: 147 }).map((_, index) => ({
    ename: `${index}` % 3 == 0 ? `示波器${index}` : (`${index}` % 4 == 0 ? `电脑${index}` : `万用表${index}`),
    ekey: `${index}`,
    etype: `${index}` % 3 == 0 ? `示波器` : (`${index}` % 4 == 0 ? `电脑` : `万用表`),
    estatus: `${index}` % 5 == 0 ? `损坏` : `正常`,
    useraccount:'admin1',
    addtime: new Date('2023-12-01')
}));
testdata.push({
    ename:"手机",
    ekey:"67",
    etype:"手机",
    estatus:"正常",
    useraccount:"admin3",
    addtime:new Date('2022-01-12')

})
/*equipment object  //
    {
        "ename":"",
        "ekey":"",
        "etype":"",
        "estatus":"",//正常、损坏、
        "useraccount":"",
        "addtime":date,        
    }
    后端返回的接口也必须是这个
*/
export default testdata;