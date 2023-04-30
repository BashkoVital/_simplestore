import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class HttpGoodsService {

  constructor(private http: HttpClient) {
  }

  getAllGoodsData() {
    return this.http.get<any>('http://localhost:8080/store/goods')
  }

  addGoods(data: any) {
    return this.http.post<any>('http://localhost:8080/store/goods', data);
  }

  updateGoodsById(data: any, id:number) {
    return this.http.put('http://localhost:8080/store/goods/' + id, data);
  }

  deleteGoodsById(id:number) {
    return this.http.delete('http://localhost:8080/store/goods/' + id);
  }
}
