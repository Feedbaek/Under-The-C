import { atom } from 'recoil';

export interface ILecture {
	evaluationID: number,
    userID: string,
    lectureName: string,
    professorName: string,
    lectureYear: number,
    semesterDivide: string,
    lectureDivide: string,
    evaluationTitle: string,
    evaluationContent: string,
    totalScore: string,
    creditScore: string,
    comfortableScore: string,
    lectureScore: string,
    likeCount: number,
    created: string,
    updated: string,
}

export const lectureListState = atom<ILecture []>({
	key: 'lectureList',
	default: [],
});