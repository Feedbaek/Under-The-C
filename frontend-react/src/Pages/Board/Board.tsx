import React from 'react';
import { useNavigate } from 'react-router';
import { useMutation } from 'react-query';
import { useForm } from 'react-hook-form';
import { ILecture } from '../../Atoms/Lecture';
import { lectorePost } from './api';

const Board = () => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm<ILecture>();
  const navigate = useNavigate();
  const mutation = useMutation(lectorePost, {
    onSuccess: (data) => {
      navigate("/");
      console.log('Response:', data);
    },
    onError: (error) => {
      console.error('Error:', error);
    },
  });
  const onSubmit = (data: ILecture) => {
    mutation.mutate(data);
    console.log('ILecture:', data);
    reset();
  };

  return (
    <section className="bg-gray-100 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label htmlFor="evaluationTitle" className="block mb-2 text-sm font-medium text-gray-900">
                제목
              </label>
              <input
                type="text"
                {...register('evaluationTitle', { required: '제목을 입력하세요' })}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-3"
              />
            </div>
            <div>
              <label htmlFor="totalScore" className="block mb-2 text-sm font-medium text-gray-900">
                별점
              </label>
              <input
                type="number"
                placeholder="1~5 입력"
                {...register('totalScore', {min:1 , max:5 })}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-3"
              />
            </div>
            <div>
              <label htmlFor="lectureName" className="block mb-2 text-sm font-medium text-gray-900">
                강의명
              </label>
              <input
                type="text"
                {...register('lectureName', { required: '강의명을 입력하세요' })}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-3"
              />
            </div>
            <div>
              <label htmlFor="professorName" className="block mb-2 text-sm font-medium text-gray-900">
                교수명
              </label>
              <input
                type="text"
                {...register("professorName", { required: '강의명을 입력하세요' })}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-3"
              />
            </div>
          </div>
          <br />
          <div>
            <label htmlFor='evaluationContent' className="block mb-2 text-sm font-medium text-gray-900">
              강의 설명
            </label>
            <textarea
              {...register('evaluationContent', { minLength:{
                value: 6,
                message: '6글자 이상 입력해주세요',
              },  
            })}
              className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-3 h-48 resize-none"
            ></textarea>
          </div>
          <button type="submit" className="w-full text-gray-900 bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-base px-5 py-3 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">
            제출하기
          </button>
        </form>
      </div>
    </section>
  );
};

export default Board;
